package com.project.learnasl.database

class UserRepository(private val dao: UserDao) {

    suspend fun insertUser(user: User) {
        dao.insertDao(user)
    }

    suspend fun getUserCount(): Int {
        return dao.getUserCount()
    }

    // for returning the only user in DB
    suspend fun getUser(): User? {
        return dao.getUser()
    }

    suspend fun addExperience(exp: Int) {
        val user = dao.getUser() ?: return
        user.addExperience(exp)
        user.computeLevel() // changes the exp into levels
        dao.updateUser(user) // saves (updates) the exp change to db
    }

    suspend fun computeLvl() : Int? {
       val lvl = getUser()?.computeLevel()
        return lvl
    }

}