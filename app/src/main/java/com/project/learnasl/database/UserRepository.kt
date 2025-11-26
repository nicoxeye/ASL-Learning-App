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

}