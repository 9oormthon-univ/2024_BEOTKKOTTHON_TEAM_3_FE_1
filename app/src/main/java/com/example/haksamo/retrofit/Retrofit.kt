package com.example.haksamo.retrofit

import android.app.Service
import android.util.Log
import android.view.View
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor() {

    companion object {
        @Volatile
        private var instance: RetrofitClient? = null
        private lateinit var userRetrofitInterface: Service
        private val baseUrl = "http://192.168.219.107:8080/api/mog/user/"

        //private val baseUrl = "http://192.168.219.110:8080/api/mog/user/"


        fun getInstance(): RetrofitClient {
            return instance ?: synchronized(this) {
                instance ?: RetrofitClient().also { instance = it }
            }
        }

        fun getUserRetrofitInterface(): Service {
            return userRetrofitInterface
        }
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃 설정
            .readTimeout(30, TimeUnit.SECONDS)    // 읽기 타임아웃 설정
            .writeTimeout(30, TimeUnit.SECONDS)   // 쓰기 타임아웃 설정
            .protocols(listOf(Protocol.HTTP_1_1))
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        userRetrofitInterface = retrofit.create(Service::class.java)
    }
}


//// 서버에서 데이터를 가져오는 함수
//private fun fetchDataFromServer() {
//    // Retrofit 인스턴스 생성
//    RetrofitClient.getInstance()
//    service = RetrofitClient.getUserRetrofitInterface()
//    call = service.sendCallData(phoneNumberDTO)
//
//    progressBar = findViewById(R.id.progressBar)
//    progressBar.visibility = View.VISIBLE
//
//    // 서버로부터 데이터를 가져오는 요청 보내기
//    call.enqueue(object : Callback<PhoneListDTO> {
//        override fun onResponse(call: Call<PhoneListDTO>, response: Response<PhoneListDTO>) {
//            progressBar.visibility = View.GONE
//            if (response.isSuccessful) {
//                val PhoneListDTO = response.body() // 서버에서 받은 데이터
//                PhoneListDTO?.phones?.let { phones ->
//                    for (phoneDTOList in phones) {
//                        for (phoneDTO in phoneDTOList) {
//                            Log.d("로그", "${phoneDTO.name}, ${phoneDTO.phoneNumbers}, Url: ${phoneDTO.profileImageUrl}")
//                            val callListItem = CallListItem(phoneDTO.name, phoneDTO.phoneNumbers, phoneDTO.profileImageUrl)
//                            callViewModel.addListItem(callListItem) // 뷰 모델에 서버에서 가져온 데이터 추가
//                        }
//                        // 중첩된 리스트에 대해 이중 반복문을 사용하여 phoneNumber 추출
//                        appPhoneNumbers = phones
//                            .flatMap { it } // 중첩된 리스트를 하나의 리스트로 평탄화
//                            .mapNotNull { it.phoneNumbers } // 각 PhoneDTO에서 phoneNumber 추출하고 null 필터링
//                        Log.d("로그", "appPhoneNumbers : ${appPhoneNumbers}")
//                    }
//                }
//
//                // 서버에서 받은 번호와 일치하지 않는 번호만 남겨두기 위한 필터링
//                val filteredContactListMap = contactListMap.filter { contact ->
//                    !appPhoneNumbers.contains(contact.second) // appPhoneNumbers에 해당 번호가 없는 경우만 필터링
//                }
//                contactListMap.clear() // 기존의 데이터를 모두 지움
//                contactListMap.addAll(filteredContactListMap) // 필터링된 데이터만 추가
//
//                //filteredContactListMap의 모든 항목 반복해서 아이템 만들고 뷰 모델에 추가
//                for (contact in filteredContactListMap) {
//                    val inviteListItem = InviteListItem(contact.first, contact.second)
//                    inviteViewModel.addListItem(inviteListItem)
//                }
//
//            } else {
//                // 요청 실패 처리
//                Log.d("로그", "데이터 요청 실패. 응답 코드: ${response.code()}, "
//                        + "오류 메시지: ${response.errorBody()?.string()}")
//            }
//        }
//
//        override fun onFailure(call: Call<PhoneListDTO>, t: Throwable) {
//            // 통신 실패 처리
//            progressBar.visibility = View.GONE
//            Log.d("로그", "통신 실패: ${t.message}")
//        }
//    })
//}