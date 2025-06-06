package com.example.talkative.Screens.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkative.model.Message
import com.example.talkative.model.MessageResponse
import com.example.talkative.repository.ChatRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: ChatRepository):ViewModel() {

    // parsing json , being safe
    val json = Json { ignoreUnknownKeys=true }

    private val _messages= MutableStateFlow<List<Message>>(emptyList())
    val messages=_messages.asStateFlow()

    private val _status=MutableStateFlow("Connecting.....")
    val status=_status.asStateFlow()

    // Add a check to prevent adding echoed messages you sent
    private var lastSentMessage: String? = null


//    init {
//        ConnectAndObserve()
//
//    }

  suspend  fun ConnectAndObserve(username:String){
        repository.connectSocket(username=username)

        viewModelScope.launch{
            repository.observeStatus().collect{st->
                Log.d("April", "${st} ")
                _status.value=st
            }
        }

        viewModelScope.launch (Dispatchers.IO){
            repository.observeMessages().collect{msg->
                try {
                    Log.d("Sans", "ConnectAndObserve:${msg} ")
                    val incomming=json.decodeFromString<MessageResponse>(msg)
                    if (incomming.sender!=username) {
                     //   getUsername.invoke(incomming.sender)
                        _messages.update { currentList ->
                            currentList + Message.Recieved(sender =incomming.sender.trim() , text =  incomming.message.trim())
                        }
                    }
                }catch (e:Exception){
                    Log.d("Sans", "ConnectAndObserve: ${e.message}")
                }
                lastSentMessage = null
            }
        }

    }

    fun SendMessages(msg:String){
        if(msg.isNotBlank()){
            lastSentMessage=msg
            _messages.update {currentList->
                currentList + Message.Sent(msg.trim())
            }
            repository.sendMessage(msg)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.closeSocket()
    }

}
