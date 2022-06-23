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
                Bukkit.getLogger().warning(String.format("The language: %1\$s is not supported. Uses English instead.", langRaw))
                "en"
            }
        }
    }
    fun getPreShutdownNoticeMessage(shutdownDelayTime: Long, tps: Double): String {
        val preShutdownMessage = messagesConfig.getString(String.format("%s.reboot.preShutdownNoticeMessage", lang))
        return if(preShutdownMessage is String) { String.format(preShutdownMessage, shutdownDelayTime)  } else { "%s.reboot.preShutdownNoticeMessage" }
    }
    fun getPluginLoadMessage() : String {
        val pluginLoadMessage = messagesConfig.getString(String.format("%s.system.pluginLoadMessage", lang))
        return if(pluginLoadMessage is String) { pluginLoadMessage  } else { "%s.system.pluginLoadMessage" }
    }
    companion object {

    }
}