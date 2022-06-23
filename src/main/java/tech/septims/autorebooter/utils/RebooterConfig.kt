package tech.septims.autorebooter.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

class RebooterConfig : ConfigBase {

    private var rebooterConfig: FileConfiguration
    constructor(plugin: Plugin, file: String): super(plugin, file){
        super.saveDefaultConfig()
        rebooterConfig = super.getConfig()!!
    }

    fun getTickThreshold(): Long {
        return rebooterConfig.getLong("tick-threshold")
    }
    fun getShutdownDelayTime(): Long {
        return rebooterConfig.getLong("shutdown-delay-time")
    }
    fun getCheckInterval(): Long {
        return rebooterConfig.getLong("check-interval")
    }
    fun getTickReference(): Int {
        if(rebooterConfig.getInt("tick-reference") in 0..3){
            return rebooterConfig.getInt("tick-reference")
        }
        return 1
    }
    fun getRecoveryRecover(): Boolean{
        return rebooterConfig.getBoolean("tick-recovery-recover")
    }
    fun getRecoveryThreshold(): Double{
        return rebooterConfig.getDouble("tick-recovery-threshold")
    }
    fun getRecoveryRecoverDelay(): Boolean{
        return rebooterConfig.getBoolean("tick-recovery-recover-delay")
    }
    fun getRecoveryRecoverDelayTime(): Long{
        return rebooterConfig.getLong("tick-recovery-recover-delay-time")
    }

}