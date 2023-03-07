import android.content.Context
import android.util.Log
import com.analytiks.core.CoreAddon
import com.analytiks.core.EventsExtension
import com.analytiks.core.UserProfileExtension
import com.analytiks.core.formatters.JSONFormatStrategy
import com.analytiks.core.model.Param
import com.analytiks.core.model.UserProperty
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

const val TAG = "AnalyticsClient"

class MixpanelAnalyticsClient(
    private val token: String,
    private val optOutTrackingDefault: Boolean = false,
    private val superProperties: JSONObject? = null,
    private val instanceName: String? = null,
    private val trackAutomaticEvents: Boolean = true,
) : CoreAddon, EventsExtension, UserProfileExtension {

    private lateinit var mixpanelClient: MixpanelAPI

    override fun initialize(context: Context) {
        mixpanelClient = MixpanelAPI.getInstance(
            context,
            this.token,
            this.optOutTrackingDefault,
            this.superProperties,
            this.instanceName,
            this.trackAutomaticEvents,
        )
    }

    override fun reset() = Unit

    override fun logEvent(name: String) {
        mixpanelClient.track(name)
    }

    override fun logEvent(name: String, vararg properties: Param) {
        val formattedProps = JSONFormatStrategy().invoke(*properties)
        mixpanelClient.track(name, formattedProps)
    }

    override fun identify(userId: String?) {
        Log.d(TAG, " Mixpanel identified")
    }

    override fun setUserProperty(property: UserProperty) {
        Log.d(TAG, " Mixpanel Property Set")
    }
}