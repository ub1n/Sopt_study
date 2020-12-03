package kr.ac.smu.cs.sopt_study.Response

import java.util.*

data class ResponseSearchBody(val meta:Meta,val documents:List<Documents>)
{
    data class Meta(val total_count:Int,val pageable_count:Int,val is_end:Boolean)
}
