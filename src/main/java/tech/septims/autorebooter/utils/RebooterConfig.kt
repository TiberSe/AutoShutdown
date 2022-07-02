package tech.septims.autorebooter.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

class RebooterConfig : ConfigBase {

    private var rebooterConfig: FileConfiguration
    constructor(plugin: Plugin, file: String): super(plugin, file){
        super.saveDefaultConfig()
        rebooterConfig = super.getConfig()!!
    }

    fun getTickThreshold(): Double {
        return rebooterConfig.getDouble("tick-threshold", 9.5)
    }
    fun getShutdownDelayTime(): Long {
        return rebooterConfig.getLong("shutdown-delay-time", 60)
    }
    fun getCheckInterval(): Long {
        return rebooterConfig.getLong("check-interval", 180)
    }
    fun getTickReference(): Int {
        if(rebooterConfig.getInt("tick-reference") in 0..3){
            return rebooterConfig.getInt("tick-reference")
        }
        return 1
    }
    fun getRecoveryRecover(): Boolean{
        return rebooterConfig.getBoolean("tick-recovery-recover", true)
    }
    fun getRecoveryThreshold(): Double{
        return rebooterConfig.getDouble("tick-recovery-threshold", 15.5)
    }
    fun getRecoveryRecoverDelay(): Boolean{
        return rebooterConfig.getBoolean("tick-recovery-recover-delay", false)
    }
    fun getRecoveryRecoverDelayTime(): Long{
        return rebooterConfig.getLong("recovery-recover-delay-time", 60)
    }

}