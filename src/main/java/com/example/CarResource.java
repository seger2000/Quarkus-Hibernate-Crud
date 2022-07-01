package com.example;
import io.quarkus.panache.common.Sort;
import org.jboss.resteasy.reactive.RestForm;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@Path("/car")
@Produces({MediaType.APPLICATION_JSON})
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {
    @GET
    @Path("/getAll")
    public List<Car> list() {
        return Car.listAll();
    }
    @GET
    @Path("/{id}")
    public Car get( Long id) {
        Car car  = Car.findById(id);
        return car;
    }


    @GET
    @Path("/{pageIndex}/{pageSize}/{name}/{sortOrder}")
    public List<Car> getbetween( long pageIndex,long pageSize,String name,String sortOrder){
        if (sortOrder.equals("ascend")) {
            return Car.list("from Car c  where c.id BETWEEN ?1 and ?2", Sort.by(name).ascending(), pageIndex, pageSize);
        }else if(sortOrder.equals("descend")){
            return Car.list("from Car c  where c.id BETWEEN ?1 and ?2", Sort.by(name).descending(), pageIndex, pageSize);
        }
        return Car.list("from Car c  where c.id BETWEEN ?1 and ?2",  pageIndex, pageSize);
    }

    @PUT
    @Path("/carindeposit")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addCar(@RestForm String name,
                           @RestForm String model,
                           @RestForm int numberMoldel,
                           @RestForm Long depositId) {
        Deposit deposit = Deposit.findById(depositId);
        if (deposit == null) {
            return Response.status(404).build();
        }

        Car car = new Car();
        car.name = name;
        car.model = model;
        car.numbermoldel = numberMoldel;
        car.datatime = LocalDateTime.now();
        car.deposit = deposit;
        car.persist();

        deposit.car.add(car);
        deposit.persist();
        return Response.status(201).build();
    }

    @PATCH
    @Path("/updatecar")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateCar(@RestForm int numberMoldel,@RestForm Long carId) {
        Car car = Car.findById(carId);
        if (car == null) {
            return Response.status(404).build();
        }
        car.numbermoldel = numberMoldel;
        car.persist();
        return Response.status(200).build();
    }

    @PATCH
    @Path("/updatecar/{id}")
    @Transactional
    public Response updateCar(Long id, Car car){
        Car newcar = Car.findById(id);
        newcar.name = car.name;
        newcar.model = car.model;
        newcar.numbermoldel = car.numbermoldel;
        newcar.persist();
        return Response.status(200).build();

    }


    @POST
    @Path("deposit")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addDeposit(@RestForm String name_deposit, @RestForm String country) {
        Deposit deposit = new Deposit();
        deposit.name_deposit = name_deposit;
        deposit.country = country;
        deposit.persist();
    }

    @POST
    @Path("addcar")
    @Transactional
    public Response addCar(Car car) {
        car.datatime = LocalDateTime.now();
        car.persist();

        if (car.isPersistent()){
            return  Response.status(200).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public void deleteCar( Long id) {
        Car car = Car.findById(id);
        if (car != null) {
//            car.deposit.car.remove(car);
            car.delete();
        }
    }


}