package com.example.upload

import com.example.common.ActionHandler
import com.example.common.Status
import com.example.common.StatusListener

class UploadManager : ActionHandler {


    private var statusListener: StatusListener? = null

    override fun addStatusListener(listener: StatusListener) {
        this.statusListener = listener
    }

    override fun start() {
        this.statusListener?.onStatusChange(Status.OnStart("Uploading started..."))

        Utils.startTimerTask(10_000) {
            if (it >= 100) {
                this.statusListener?.onStatusChange(Status.OnCompleted("Uploading completed!"))
                return@startTimerTask
            }

            this.statusListener?.onStatusChange(Status.OnInProgress("Uploaded ", it))
        }
    }

    override fun cancel() {
        Utils.stopTimerTask {
            this.statusListener?.onStatusChange(Status.OnError("Uploading cancelled!"))
        }
    }

    override fun stop() {
        Utils.stopTimerTask {
            this.statusListener?.onStatusChange(Status.OnError("Uploading stopped!"))
        }
    }
}