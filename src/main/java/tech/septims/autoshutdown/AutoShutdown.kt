package tech.septims.autoshutdown

import net.minecraft.server.MinecraftServer
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import tech.septims.autoshutdown.utils.MessagesConfig
import tech.septims.autoshutdown.utils.ShutdownConfig

class AutoShutdown : JavaPlugin() {
    private lateinit var config: ShutdownConfig
    private lateinit var messageConfig: MessagesConfig
    private lateinit var tpsChecker: BukkitTask
    private var tpsThreshold: Double = 9.5
    private var tickReference : Int = 1
    private var shutdownDelayTime: Long = 60
    private var recoveryRecover: Boolean = false
    private var recoveryThreshold: Double = 15.5
    private var recoveryRecoverDelay: Boolean = false
    private var recoveryRecoverDelayTime: Long = 60

    override fun onEnable() {
        config = ShutdownConfig(this, "config.yml")
        messageConfig = MessagesConfig(this, "messages.yml")
        loadAndSetConfig()
        Bukkit.getLogger().info(messageConfig.getPluginLoadMessage(shutdownDelayTime, tpsThreshold))
        tpsChecker = Bukkit.getScheduler().runTaskTimer(this, Runnable { tpsCheck() }, 20 * config.getCheckInterval(), 20 * config.getCheckInterval())
    }

    override fun onDisable() {
        tpsChecker.cancel()
    }

    private fun tpsCheck(){
        if(MinecraftServer.getServer().recentTps[tickReference] < tpsThreshold){
            if(recoveryRecoverDelay){
                Bukkit.getLogger().info(messageConfig.getRecoverStartedMessage(recoveryRecoverDelayTime, tpsThreshold))
                Bukkit.getScheduler().runTaskLater(this, Runnable { waitRecovery() }, 20 * recoveryRecoverDelayTime)
                return
            }else{
                Bukkit.broadcastMessage(messageConfig.getPreShutdownNoticeMessage(shutdownDelayTime, recoveryThreshold))
                Bukkit.getScheduler().runTaskLater(this, Runnable { shutdown() }, 20 * shutdownDelayTime)
                return
            }
        }
    }
    private fun shutdown(){
        if(MinecraftServer.getServer().recentTps[tickReference] > recoveryThreshold){
            Bukkit.broadcastMessage(messageConfig.getTPSRecoveredMessage())
            return
        }
        val countdownMessage = messageConfig.getServerShutdownCountdownMessage()
        var countdown = config.getShutdownCountdownTime()
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            countdown--
            Bukkit.broadcastMessage(countdownMessage.format(countdown))
            if(countdown == 0) {
                Bukkit.broadcastMessage(messageConfig.getServerShutdownMessage())
                Bukkit.shutdown()
            }
        },0, 20)
        return
    }

    private fun waitRecovery(){
        if(MinecraftServer.getServer().recentTps[tickReference] > recoveryThreshold){
            Bukkit.getLogger().info(messageConfig.getRecoverRecoveredMessage(MinecraftServer.getServer().recentTps[tickReference]))
            return
        }
        Bukkit.broadcastMessage(messageConfig.getPreShutdownNoticeMessage(shutdownDelayTime, recoveryThreshold))
        Bukkit.getScheduler().runTaskLater(this, Runnable { shutdown() }, 20 * shutdownDelayTime)
        return
    }

    private fun loadAndSetConfig(){
        tpsThreshold = config.getTickThreshold()
        tickReference = config.getTickReference()
        shutdownDelayTime = config.getShutdownDelayTime()
        recoveryRecover = config.getRecoveryRecover()
        recoveryThreshold = config.getRecoveryThreshold()
        recoveryRecoverDelay = config.getRecoveryRecoverDelay()
        recoveryRecoverDelayTime = config.getRecoveryRecoverDelayTime()
    }


}