package bitc.fullstack.app.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BranchDTO(

    @SerializedName("branchName")
    var branchName: String = "",

    @SerializedName("branchPhone")
    var branchPhone: String = "",

    @SerializedName("branchZipCode")
    var branchZipCode: String = "",

    @SerializedName("branchRoadAddr")
    var branchRoadAddr: String = ""

) : Serializable
