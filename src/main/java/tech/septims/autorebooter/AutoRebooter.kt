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
    private var tpsThreshold: Long = 10
    private var tickReference : Int = 1
    private var shutdownDelayTime: Long = 60
    private var recoveryRecover: Boolean = false
    private var recoveryRecoverDelay: Boolean = false
    private var recoveryRecoverDelayTime: Long = 60

    override fun onEnable() {
        config = RebooterConfig(this, "config.yml")
        messageConfig = MessagesConfig(this, "messages.yml")
        Bukkit.getLogger().info(messageConfig.getPluginLoadMessage())
        tpsThreshold = config.getTickThreshold()
        tickReference = config.getTickReference()
        shutdownDelayTime = config.getShutdownDelayTime()
        recoveryRecover = config.getRecoveryRecover()
        recoveryRecoverDelay = config.getRecoveryRecoverDelay()
        recoveryRecoverDelayTime = config.getRecoveryRecoverDelayTime()
        tpsChecker = Bukkit.getScheduler().runTaskTimer(this, Runnable { tpsCheck() }, 0L, 20 * config.getCheckInterval())
    }

    override fun onDisable() {

    }

    private fun tpsCheck(){
        if(MinecraftServer.getServer().recentTps[tickReference] < tpsThreshold){
            if(recoveryRecoverDelay){
                Bukkit.getScheduler().runTaskLater(this, Runnable { waitRecovery() }, 20 * recoveryRecoverDelayTime)
                return
            }else{
                Bukkit.broadcastMessage(messageConfig.getPreShutdownNoticeMessage(shutdownDelayTime, MinecraftServer.getServer().recentTps[tickReference]))
                Bukkit.getScheduler().runTaskLater(this, Runnable { shutdown() }, 20 * shutdownDelayTime)
            }
        }
    }
    private fun shutdown(){

    }

    private fun waitRecovery(){

    }


}