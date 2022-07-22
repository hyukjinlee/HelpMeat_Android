package com.project.helpmeat.controller

class GrillSettingsDataController {
    companion object {
        const val TAG = "GrillSettingsDataController"
    }

    private val mObserverList = ArrayList<GrillSettingsDataObserver>()

    fun addObserver(observer: GrillSettingsDataObserver) {
        mObserverList.add(observer)
    }

    fun removeObserverAll() {
        mObserverList.clear()
    }

    fun notifyMeatSelected(meatValue: Int) {
        mObserverList.forEach { o ->
            o.onMeatSelected(meatValue)
        }
    }

    fun notifyWidthSelected(widthValue: Float) {
        mObserverList.forEach { o ->
            o.onWidthSelected(widthValue)
        }
    }

}
interface GrillSettingsDataObserver {
    fun onMeatSelected(meatValue: Int)

    fun onWidthSelected(width: Float)

    fun onDegreeSelected()

    fun onGrillSelected()
}