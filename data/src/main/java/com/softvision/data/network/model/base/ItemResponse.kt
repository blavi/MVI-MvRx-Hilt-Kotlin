package com.softvision.data.network.model.base

import com.softvision.data.mappers.ItemDomainMapper
import com.softvision.data.mappers.ItemRoomMapper

interface ItemResponse<out E : Any?, C : Any, out D: Any>: ItemRoomMapper<E, C>, ItemDomainMapper<D>