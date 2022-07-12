package com.project.helpmeat.controller

class GrillSettingsDataController {
    companion object {
        const val TAG = "GrillSettingsDataController"
    }

    private val mObserverList = ArrayList<GrillSettingsDataObserver>()

    private var mMeatValue = 0

    fun addObserver(observer: GrillSettingsDataObserver) {
        mObserverList.add(observer)
    }

    fun removeObserver(observer: GrillSettingsDataObserver) {
        mObserverList.remove(observer)
    }

    fun removeObserverAll() {
        mObserverList.clear()
    }

    fun onMeatSelected(meatValue: Int) {
        mMeatValue = meatValue
    }

    fun notifyMeatCompleted() {
        mObserverList.forEach { o ->
            o.onMeatSelected(mMeatValue)
        }
    }

}
interface GrillSettingsDataObserver {
    fun onMeatSelected(meatValue: Int)

    fun onWidthSelected()

    fun onGrillSelected()

    fun onDegreeSelected()
}