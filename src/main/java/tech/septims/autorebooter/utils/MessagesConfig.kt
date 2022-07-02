package tech.septims.autorebooter.utils

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

class MessagesConfig : ConfigBase {


    constructor(plugin: Plugin, file: String): super(plugin, file){
        super.saveDefaultConfig()
        this.messagesConfig = super.getConfig()!!
        this.lang = getLanguage()
    }
    private lateinit var lang : String
    private lateinit var messagesConfig: FileConfiguration
    private fun getLanguage() : String {
        return when(val langRaw = messagesConfig.getString("lang")){
            "ja" -> "ja"
            "en" -> "en"
            else -> {
                Bukkit.getLogger().warning("The language: %s is not supported. Uses English instead.".format(langRaw))
                "en"
            }
        }
    }
    fun getPreRestartNoticeMessage(shutdownDelayTime: Long, recoverThreshold: Double): String {
        val preShutdownMessage = messagesConfig.getString(String.format("%s.ingame.preRestartNoticeMessage", lang))
        return if(preShutdownMessage is String) { preShutdownMessage.format(shutdownDelayTime, recoverThreshold)  } else { "%s.ingame.preRestartNoticeMessage" }
    }
    fun getTPSRecoveredMessage() : String{
        val tpsRecoveredMessage = messagesConfig.getString("%s.ingame.whenTPSRecoveredToThreshold".format(lang))
        return if(tpsRecoveredMessage is String) { return tpsRecoveredMessage } else { "%s.ingame.whenTPSRecoveredToThreshold" }
    }

    fun getServerRestartMessage() : String {
        val serverRestartMessage = messagesConfig.getString("%s.ingame.inRestartProgressMessage".format(lang))
        return if(serverRestartMessage is String) { return serverRestartMessage } else { "%s.ingame.inRestartProgressMessage" }
    }
    fun getPluginLoadMessage(shutdownDelayTime: Long, tpsThreshold: Double) : String {
        val pluginLoadMessage = messagesConfig.getString(String.format("%s.system.pluginLoadMessage", lang))
        //String.format(pluginLoadMessage, shutdownDelayTime, recoverThreshold)
        return if(pluginLoadMessage is String) { pluginLoadMessage.format(tpsThreshold, shutdownDelayTime)} else { "%s.system.pluginLoadMessage" }
    }

    fun getRecoverStartedMessage(recoverDelayTime : Long, tpsThreshold: Double) : String{
        val recoverStartedMessage = messagesConfig.getString(String.format("%s.system.recoveryRecoverStarted", lang))
        return if(recoverStartedMessage is String) { recoverStartedMessage.format(tpsThreshold, recoverDelayTime)} else { "%s.system.recoveryRecoverStarted" }
    }

    fun getRecoverRecoveredMessage(tps: Double): String {
        val recoverRecoveredMessage = messagesConfig.getString(String.format("%s.system.recoveryRecoverRecovered", lang))
        return if(recoverRecoveredMessage is String) { recoverRecoveredMessage.format(tps)} else { "%s.system.recoveryRecoverRecovered" }
    }
}