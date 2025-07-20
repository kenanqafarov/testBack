package com.rustam.modern_dentistry.service;

import com.rustam.modern_dentistry.dao.entity.WorkersWorkSchedule;
import com.rustam.modern_dentistry.dao.entity.users.BaseUser;
import com.rustam.modern_dentistry.dao.repository.WorkersWorkScheduleRepository;
import com.rustam.modern_dentistry.dto.request.create.WorkersWorkScheduleRequest;
import com.rustam.modern_dentistry.dto.request.read.WorkersWorkScheduleSearchRequest;
import com.rustam.modern_dentistry.dto.response.read.WorkersWorkScheduleResponse;
import com.rustam.modern_dentistry.dto.response.update.WorkersWorkScheduleUpdateDTO;
import com.rustam.modern_dentistry.exception.custom.WorkersWorkScheduleNotFoundException;
import com.rustam.modern_dentistry.mapper.WorkersWorkScheduleMapper;
import com.rustam.modern_dentistry.util.UtilService;
import com.rustam.modern_dentistry.util.specification.WorkersWorkScheduleSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkersWorkScheduleService {

    WorkersWorkScheduleRepository workersWorkScheduleRepository;
    UtilService utilService;
    WorkersWorkScheduleMapper workersWorkScheduleMapper;

    public void create(WorkersWorkScheduleRequest workersWorkScheduleRequest) {
        BaseUser baseUser = utilService.findByBaseUserId(workersWorkScheduleRequest.getUserId());
        WorkersWorkSchedule workersWorkSchedule = WorkersWorkSchedule.builder()
                .weekDay(workersWorkScheduleRequest.getWeekDay())
                .room(workersWorkScheduleRequest.getRoom())
                .worker(baseUser)
                .startTime(workersWorkScheduleRequest.getStartTime())
                .finishTime(workersWorkScheduleRequest.getFinishTime())
                .build();
        workersWorkScheduleRepository.save(workersWorkSchedule);
    }

    public List<WorkersWorkScheduleResponse> read() {
        return workersWorkScheduleRepository.findAllWorkersWorkSchedule();
    }

    public WorkersWorkSchedule findById(Long id){
        return workersWorkScheduleRepository.findById(id)
                .orElseThrow(() -> new WorkersWorkScheduleNotFoundException("No such work calendar was found."));
    }

    public WorkersWorkScheduleUpdateDTO update(WorkersWorkScheduleUpdateDTO workersWorkScheduleUpdateDTO) {
        WorkersWorkSchedule workersWorkSchedule = findById(workersWorkScheduleUpdateDTO.getId());
        if (workersWorkScheduleUpdateDTO.getWeekDay() != null){
            workersWorkSchedule.setWeekDay(workersWorkScheduleUpdateDTO.getWeekDay());
        }
        if (workersWorkScheduleUpdateDTO.getRoom() != null){
            workersWorkSchedule.setRoom(workersWorkScheduleUpdateDTO.getRoom());
        }
        if (workersWorkScheduleUpdateDTO.getStartTime() != null){
            workersWorkSchedule.setStartTime(workersWorkScheduleUpdateDTO.getStartTime());
        }
        if (workersWorkScheduleUpdateDTO.getFinishTime() != null){
            workersWorkSchedule.setFinishTime(workersWorkScheduleUpdateDTO.getFinishTime());
        }
        workersWorkScheduleRepository.save(workersWorkSchedule);
        return workersWorkScheduleMapper.toDto(workersWorkSchedule);
    }

    public void delete(Long id) {
        WorkersWorkSchedule workersWorkSchedule = findById(id);
        workersWorkScheduleRepository.delete(workersWorkSchedule);
    }

    public List<WorkersWorkScheduleResponse> search(WorkersWorkScheduleSearchRequest workersWorkScheduleSearchRequest) {
        List<WorkersWorkSchedule> workersWorkSchedules = workersWorkScheduleRepository.findAll(WorkersWorkScheduleSpecification.filterBy(workersWorkScheduleSearchRequest.getWeekDay()));
        return workersWorkScheduleMapper.toDtos(workersWorkSchedules);
    }
}
