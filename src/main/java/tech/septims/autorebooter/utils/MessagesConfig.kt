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
    fun getLanguage() : String {
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
        val preShutdownMessage = messagesConfig.getString(String.format("%s.reboot.preRestartNoticeMessage", lang))
        return if(preShutdownMessage is String) { preShutdownMessage.format(shutdownDelayTime, recoverThreshold)  } else { "%s.reboot.preRestartNoticeMessage" }
    }
    fun getPluginLoadMessage(shutdownDelayTime: Long, tpsThreshold: Double) : String {
        val pluginLoadMessage = messagesConfig.getString(String.format("%s.system.pluginLoadMessage", lang))
        //String.format(pluginLoadMessage, shutdownDelayTime, recoverThreshold)
        return if(pluginLoadMessage is String) { pluginLoadMessage.format(tpsThreshold, shutdownDelayTime)} else { "%s.system.pluginLoadMessage" }
        println(pluginLoadMessage)
    }
    fun getTPSRecoveredMessage() : String{
        val tpsRecoveredMessage = messagesConfig.getString("%s.reboot.whenTPSRecoveredToThreshold".format(lang))
        return if(tpsRecoveredMessage is String) { return tpsRecoveredMessage } else { "%s.reboot.whenTPSRecoveredToThreshold" }
    }

    fun getServerRestartMessage() : String {
        val serverRestartMessage = messagesConfig.getString("%s.reboot.inRestartProgressMessage".format(lang))
        return if(serverRestartMessage is String) { return serverRestartMessage } else { "%s.reboot.inRestartProgressMessage" }
    }
    companion object {

    }
}