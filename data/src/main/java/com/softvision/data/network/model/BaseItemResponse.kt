package com.softvision.data.network.model

import com.softvision.data.mappers.ItemRoomMapper
import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.mappers.ItemRoomMapperWithExtraInfo

interface BaseItemResponse<out E : Any?, C : Any, out D: Any>: ItemRoomMapperWithExtraInfo<E, C>, ItemDomainMapper<D>, ItemRoomMapper<E>