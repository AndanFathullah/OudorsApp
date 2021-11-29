package com.dicoding.oudorsapp.data

object Consultant {

    fun generateConsultant(): List<ConsulEntity>{
        val consul = ArrayList<ConsulEntity>()
        consul.add(ConsulEntity("1","Andre Darmaja", "Consultant", "+6282232561864"))
        consul.add(ConsulEntity("2","Randy Limpati", "Consultant", "+6281315063600"))
        consul.add(ConsulEntity("3","Gilang", "Consultant", "+6281916327534"))
        consul.add(ConsulEntity("4","Adis", "Consultant", "+6282234547205"))
        consul.add(ConsulEntity("5","Nabil HI", "Consultant", "+6285868591891"))
        return consul
    }
}