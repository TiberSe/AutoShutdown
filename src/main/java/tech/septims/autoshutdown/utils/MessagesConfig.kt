package tech.septims.autoshutdown.utils

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin

class MessagesConfig : ConfigBase {


    constructor(plugin: Plugin, file: String): super(plugin, file){
        super.saveDefaultConfig()
        this.messagesConfig = super.getConfig()!!
        this.lang = getLanguage()
    }
    private var lang : String
    private var messagesConfig: FileConfiguration
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
    fun getPreShutdownNoticeMessage(shutdownDelayTime: Long, recoverThreshold: Double): String {
        val preShutdownMessage = messagesConfig.getString(String.format("%s.ingame.preShutdownNoticeMessage", lang))
        return if(preShutdownMessage is String) { preShutdownMessage.format(shutdownDelayTime, recoverThreshold)  } else { "%s.ingame.preShutdownNoticeMessage" }
    }
    fun getTPSRecoveredMessage() : String{
        val tpsRecoveredMessage = messagesConfig.getString("%s.ingame.whenTPSRecoveredToThreshold".format(lang))
        return if(tpsRecoveredMessage is String) { return tpsRecoveredMessage } else { "%s.ingame.whenTPSRecoveredToThreshold" }
    }

    fun getServerShutdownMessage() : String {
        val serverShutdownMessage = messagesConfig.getString("%s.ingame.inShutdownProgressMessage".format(lang))
        return if(serverShutdownMessage is String) { return serverShutdownMessage } else { "%s.ingame.inShutdownProgressMessage" }
    }

    fun getServerShutdownCountdownMessage(): String {
        val serverShutdownMessage = messagesConfig.getString("%s.ingame.inShutdownCountdownMessage".format(lang))
        return if(serverShutdownMessage is String) { return serverShutdownMessage } else { "%s.ingame.inShutdownCountdownMessage" }
    }
    fun getPluginLoadMessage(shutdownDelayTime: Long, tpsThreshold: Double) : String {
        val pluginLoadMessage = messagesConfig.getString(String.format("%s.system.pluginLoadMessage", lang))
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