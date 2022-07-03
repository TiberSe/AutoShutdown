package tech.septims.autoshutdown.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

class ShutdownConfig : ConfigBase {

    private var shutdownConfig: FileConfiguration
    constructor(plugin: Plugin, file: String): super(plugin, file){
        super.saveDefaultConfig()
        shutdownConfig = super.getConfig()!!
    }

    fun getTickThreshold(): Double {
        return shutdownConfig.getDouble("tick-threshold", 9.5)
    }
    fun getShutdownDelayTime(): Long {
        return shutdownConfig.getLong("shutdown-delay-time", 60)
    }
    fun getShutdownCountdownTime() : Int {
        return shutdownConfig.getInt("shutdown-countdown-time", 10)
    }
    fun getCheckInterval(): Long {
        return shutdownConfig.getLong("check-interval", 180)
    }
    fun getTickReference(): Int {
        if(shutdownConfig.getInt("tick-reference") in 0..3){
            return shutdownConfig.getInt("tick-reference")
        }
        return 1
    }
    fun getRecoveryRecover(): Boolean{
        return shutdownConfig.getBoolean("tick-recovery-recover", true)
    }
    fun getRecoveryThreshold(): Double{
        return shutdownConfig.getDouble("tick-recovery-threshold", 15.5)
    }
    fun getRecoveryRecoverDelay(): Boolean{
        return shutdownConfig.getBoolean("tick-recovery-recover-delay", false)
    }
    fun getRecoveryRecoverDelayTime(): Long{
        return shutdownConfig.getLong("recovery-recover-delay-time", 60)
    }

}