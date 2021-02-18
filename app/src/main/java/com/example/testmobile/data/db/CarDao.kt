package com.example.testmobile.data.db

import androidx.room.*
import com.example.testmobile.data.models.*

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCar(vararg cars: Car)

    @Delete
    fun deleteCar(vararg cars: Car)

    @Update
    fun updateCar(vararg cars: Car)

    @Query("Select * from cars")
    fun getAllCars(): List<Car>

    @Query(
        """
    select *
    from Cars inner join CarPropertyCrossRef on cars.carId = CarPropertyCrossRef.carId
    inner join Properties on Properties.propertyId = CarPropertyCrossRef.propertyId
    where Cars.carId=:id
    """
    )
    fun getCar(id: Long): CarWithProperties


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPropertyCar(carProperty: CarPropertyCrossRef)

    @Transaction
    fun insertCarProperty(car: Car, carProperty: CarPropertyCrossRef) {
        insertCar(car)
        insertPropertyCar(carProperty)
    }
}