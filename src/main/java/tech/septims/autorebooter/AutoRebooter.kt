package tech.septims.autorebooter

import net.minecraft.server.MinecraftServer
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import tech.septims.autorebooter.utils.MessagesConfig
import tech.septims.autorebooter.utils.RebooterConfig

class AutoRebooter : JavaPlugin() {
    private lateinit var config: RebooterConfig
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
        config = RebooterConfig(this, "config.yml")
        messageConfig = MessagesConfig(this, "messages.yml")
        tpsThreshold = config.getTickThreshold()
        tickReference = config.getTickReference()
        shutdownDelayTime = config.getShutdownDelayTime()
        recoveryRecover = config.getRecoveryRecover()
        recoveryThreshold = config.getRecoveryThreshold()
        recoveryRecoverDelay = config.getRecoveryRecoverDelay()
        recoveryRecoverDelayTime = config.getRecoveryRecoverDelayTime()
        Bukkit.getLogger().info(messageConfig.getPluginLoadMessage(shutdownDelayTime, tpsThreshold))
        tpsChecker = Bukkit.getScheduler().runTaskTimer(this, Runnable { tpsCheck() }, 20 * config.getCheckInterval(), 20 * config.getCheckInterval())
    }

    override fun onDisable() {

    }

    private fun tpsCheck(){
        if(MinecraftServer.getServer().recentTps[tickReference] < tpsThreshold){
            if(recoveryRecoverDelay){
                Bukkit.getScheduler().runTaskLater(this, Runnable { waitRecovery() }, 20 * recoveryRecoverDelayTime)
                return
            }else{
                Bukkit.broadcastMessage(messageConfig.getPreRestartNoticeMessage(shutdownDelayTime, recoveryThreshold))
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
        Bukkit.broadcastMessage(messageConfig.getServerRestartMessage())
        Bukkit.shutdown()
    }

    private fun waitRecovery(){
        if(MinecraftServer.getServer().recentTps[tickReference] > recoveryThreshold){
            Bukkit.broadcastMessage(messageConfig.getTPSRecoveredMessage())
            return
        }
        Bukkit.broadcastMessage(messageConfig.getPreRestartNoticeMessage(shutdownDelayTime, recoveryThreshold))
        Bukkit.getScheduler().runTaskLater(this, Runnable { shutdown() }, 20 * shutdownDelayTime)
        return
    }


}