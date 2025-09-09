package sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.UserNotification;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.repository.UserNotificationRepository;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.UserNotificationService;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.dto.UserNotificationDTO;
import sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.service.mapper.UserNotificationMapper;

/**
 * Service Implementation for managing {@link sn.ept.git.hackhaton.TeamRetro.LifeLinkBackend.domain.UserNotification}.
 */
@Service
@Transactional
public class UserNotificationServiceImpl implements UserNotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(UserNotificationServiceImpl.class);

    private final UserNotificationRepository userNotificationRepository;

    private final UserNotificationMapper userNotificationMapper;

    public UserNotificationServiceImpl(
        UserNotificationRepository userNotificationRepository,
        UserNotificationMapper userNotificationMapper
    ) {
        this.userNotificationRepository = userNotificationRepository;
        this.userNotificationMapper = userNotificationMapper;
    }

    @Override
    public UserNotificationDTO save(UserNotificationDTO userNotificationDTO) {
        LOG.debug("Request to save UserNotification : {}", userNotificationDTO);
        UserNotification userNotification = userNotificationMapper.toEntity(userNotificationDTO);
        userNotification = userNotificationRepository.save(userNotification);
        return userNotificationMapper.toDto(userNotification);
    }

    @Override
    public UserNotificationDTO update(UserNotificationDTO userNotificationDTO) {
        LOG.debug("Request to update UserNotification : {}", userNotificationDTO);
        UserNotification userNotification = userNotificationMapper.toEntity(userNotificationDTO);
        userNotification = userNotificationRepository.save(userNotification);
        return userNotificationMapper.toDto(userNotification);
    }

    @Override
    public Optional<UserNotificationDTO> partialUpdate(UserNotificationDTO userNotificationDTO) {
        LOG.debug("Request to partially update UserNotification : {}", userNotificationDTO);

        return userNotificationRepository
            .findById(userNotificationDTO.getId())
            .map(existingUserNotification -> {
                userNotificationMapper.partialUpdate(existingUserNotification, userNotificationDTO);

                return existingUserNotification;
            })
            .map(userNotificationRepository::save)
            .map(userNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserNotificationDTO> findAll() {
        LOG.debug("Request to get all UserNotifications");
        return userNotificationRepository
            .findAll()
            .stream()
            .map(userNotificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserNotificationDTO> findOne(Long id) {
        LOG.debug("Request to get UserNotification : {}", id);
        return userNotificationRepository.findById(id).map(userNotificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete UserNotification : {}", id);
        userNotificationRepository.deleteById(id);
    }
}
