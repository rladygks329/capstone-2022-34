package com.capstone.momomeal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.momomeal.api.MomomealService
import com.capstone.momomeal.data.Chatroom
import com.capstone.momomeal.data.User
import com.capstone.momomeal.databinding.FragmentChatInfoBinding
import com.capstone.momomeal.feature.BaseDialogFragment
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatInfoFragment : BaseDialogFragment<FragmentChatInfoBinding>(FragmentChatInfoBinding::inflate) {

    private val TAG = "ChatInfoFragment"
    val momomeal = MomomealService.momomealAPI

    val user: User by lazy{
        arguments?.getParcelable<User>("User")!!
    }
    lateinit var chatroom: Chatroom

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        binding.framgentChatInfoExit.setOnClickListener{
            dismiss()
        }
        binding.fragmentChatInfoEnter.setOnClickListener{
            enterChat()
        }
        return retView
    }
    private fun enterChat(){
        momomeal.enterChatroom(user.idUser, chatroom.idChatroom)
            .enqueue(object:Callback<HashMap<String, Int>>{
            override fun onResponse(
                call: Call<HashMap<String, Int>>,
                response: Response<HashMap<String, Int>>
            ) {
                Log.d("retrofit", response?.body().toString())
                if(response.isSuccessful.not()){
                    return
                }
                response.body()?.let{
                    val intent = Intent(activity, ChatActivity::class.java)
                    intent.putExtra("myinfo", user.trans_User_light())
                    intent.putExtra("chatroominfo", chatroom)
                    intent.putExtra("isNewChat", false)
                    startActivity(intent)
                    dismiss()
                }
            }

            override fun onFailure(call: Call<HashMap<String, Int>>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        chatroom = arguments?.getParcelable<Chatroom>("Chatroom")!!
        binding.framgentChatInfoTitle.text = chatroom.nameRoom
        binding.framgentChatInfoCategory.text = chatroom.category.KoreanName
        binding.framgentChatInfoMax.text = chatroom.maxCapacity.toString()
        binding.framgentChatInfoStore.text = chatroom.nameStore
        binding.framgentChatInfoPickup.text = chatroom.namePickupPlace

        //풀스크린보다 작게 크기를 설정한다.
        val dialogWidth = resources.displayMetrics.widthPixels * 0.9
        val dialogHeight = resources.displayMetrics.heightPixels * 0.9
        dialog?.window?.setLayout(dialogWidth.toInt(), dialogHeight.toInt())
        initView()
    }
    private fun initView() {
        val mapView = MapView(requireActivity())
        val mapViewContainer = binding.fragmentChatInfoMapContainer as ViewGroup
        Log.d("x", chatroom.coordPickupPlaceX!!.toString())
        Log.d("y", chatroom.coordPickupPlaceY!!.toString())
        val mapPoint = MapPoint.mapPointWithGeoCoord(chatroom.coordPickupPlaceY!!, chatroom.coordPickupPlaceX!!)

        mapView.setMapCenterPoint(mapPoint, true);
        mapView.setZoomLevel(1, true)

        val marker = MapPOIItem()
        marker.itemName = "near this"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker)
        mapViewContainer.addView(mapView)
    }

}
