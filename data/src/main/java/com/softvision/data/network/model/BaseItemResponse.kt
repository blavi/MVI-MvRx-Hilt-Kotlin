package com.softvision.data.network.model

import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.mappers.ItemRoomMapper

interface BaseItemResponse<out E : Any?, C : Any, out D: Any>: ItemRoomMapper<E, C>, ItemDomainMapper<D>