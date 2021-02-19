package com.example.testmobile.data.db

import androidx.room.*
import com.example.testmobile.data.models.*

@Dao
interface CarDao {

    @Query("Select * From cars Where carId=:idCar")
    fun getCar(idCar: Long): Car

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCar(cars: Car): Long

    @Query("Delete From cars WHERE carId=:idCar")
    fun deleteCar(idCar: Long)

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
    fun getCarWithProperties(id: Long): CarWithProperties


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPropertyCar(carProperty: CarPropertyCrossRef)

    @Query("Delete From CarPropertyCrossRef Where carId=:idCar")
    fun deletePropertyCar(idCar: Long)

    @Update
    fun updatePropertyCar(carProperty: CarPropertyCrossRef)

    @Query("Select *  From CarPropertyCrossRef Where carId=:idCar")
    fun selectPropertyCar(idCar: Long): List<CarPropertyCrossRef>


    // Transactions Operation
    @Transaction
    fun completeDeleteCar(idCar: Long) {
        deleteCar(idCar)
        deletePropertyCar(idCar)
    }

    @Transaction
    fun insertCarProperty(car: Car, carProperty: List<CarPropertyCrossRef>) {
        var idCar = insertCar(car)
        for (property in carProperty) {
            property.carId = idCar
            insertPropertyCar(property)
        }
    }

    @Transaction
    fun updateCarProperty(car: Car, carProperty: List<CarPropertyCrossRef>) {
        updateCar(car)
        for (property in carProperty) {
            property.carId = car.carId
            updatePropertyCar(property)
        }
    }
}