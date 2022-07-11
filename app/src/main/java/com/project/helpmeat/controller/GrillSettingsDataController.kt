package com.project.helpmeat.controller

class GrillSettingsDataController {
    companion object {
        const val TAG = "GrillSettingsDataController"
    }

    private val mObserverList = ArrayList<GrillSettingsDataObserver>()

    private var mMeatType = 0

    fun addObserver(observer: GrillSettingsDataObserver) {
        mObserverList.add(observer)
    }

    fun removeObserver(observer: GrillSettingsDataObserver) {
        mObserverList.remove(observer)
    }

    fun removeObserverAll() {
        mObserverList.clear()
    }

    fun onMeatSelected(meatType: Int) {
        mMeatType = meatType
        notifyMeatObservers()
    }

    private fun notifyMeatObservers() {
        mObserverList.forEach { o ->
            o.onMeatSelected(mMeatType)
        }
    }

}
interface GrillSettingsDataObserver {
    fun onMeatSelected(meatType: Int)

    fun onWidthSelected()

    fun onGrillSelected()

    fun onDegreeSelected()
}