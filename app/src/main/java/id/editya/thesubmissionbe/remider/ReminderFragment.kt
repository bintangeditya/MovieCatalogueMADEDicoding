package id.editya.thesubmissionbe.remider


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import id.editya.thesubmissionbe.R
import id.editya.thesubmissionbe.Utils

class ReminderFragment : Fragment() {

    private lateinit var sReReminder : Switch
    private lateinit var sDaReminder : Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sReReminder = view.findViewById(R.id.srreminder)
        sDaReminder = view.findViewById(R.id.sdreminder)

        onReleaseReminder()
        onDailyReminder()

        sReReminder.setOnCheckedChangeListener (null)
        sReReminder.isChecked = Utils.getSharedPreferences(Utils.KEY_RELEASE_REMINDER) == "1"

        sReReminder.setOnCheckedChangeListener { buttonView, isChecked ->
            if(sReReminder.isChecked) {
                Utils.putSharedPreferences(Utils.KEY_RELEASE_REMINDER,"1")
            }
            else Utils.putSharedPreferences(Utils.KEY_RELEASE_REMINDER,"0")
            onReleaseReminder()
        }

        sDaReminder.setOnCheckedChangeListener (null)
        sDaReminder.isChecked = Utils.getSharedPreferences(Utils.KEY_DAILY_REMINDER) == "1"

        sDaReminder.setOnCheckedChangeListener { buttonView, isChecked ->
            if(sDaReminder.isChecked) Utils.putSharedPreferences(Utils.KEY_DAILY_REMINDER,"1")
            else  Utils.putSharedPreferences(Utils.KEY_DAILY_REMINDER,"0")
            onDailyReminder()
        }

    }


    private fun onReleaseReminder(){
        if (Utils.getSharedPreferences(Utils.KEY_RELEASE_REMINDER) == "1"){
            NotificationUtils().setNotification( requireActivity(),Utils.KEY_RELEASE_REMINDER)}
        else NotificationUtils().offNotification(requireActivity(),Utils.KEY_RELEASE_REMINDER)
    }

    private fun onDailyReminder(){
        if (Utils.getSharedPreferences(Utils.KEY_DAILY_REMINDER) == "1"){
            NotificationUtils().setNotification( requireActivity(),Utils.KEY_DAILY_REMINDER)}
        else NotificationUtils().offNotification(requireActivity(),Utils.KEY_DAILY_REMINDER)
    }


}
