package org.egov.process.config.auth;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.AbstractManager;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.egov.process.entity.UserGroup;
import org.egov.process.service.UserGroupService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ProcessUserGroupManager extends AbstractManager implements GroupEntityManager {

    private UserGroupService userGroupService;

    public ProcessUserGroupManager(final ProcessEngineConfigurationImpl processEngineConfiguration, UserGroupService userGroupService) {
        super(processEngineConfiguration);
        this.userGroupService = userGroupService;
    }

    @Override
    public GroupEntity findById(final String entityId) {
        return mapUserGroupToGroupEntity(userGroupService.getUserGroupByName(entityId));
    }

    @Override
    public List<Group> findGroupByQueryCriteria(final GroupQueryImpl query, final Page page) {
        if (isNotBlank(query.getName()))
            return Arrays.asList(findById(query.getName()));
        if (isNotBlank(query.getNameLike()))
            return userGroupService.getUserGroupsByNameLike(query.getNameLike()).stream().
                    map(this::mapUserGroupToGroupEntity).collect(Collectors.toList());
        return null;
    }

    @Override
    public long findGroupCountByQueryCriteria(final GroupQueryImpl query) {
        return findGroupByQueryCriteria(query, null).size();
    }

    @Override
    public List<Group> findGroupsByUser(final String userId) {
        //TODO has to implement if required
        return null;
    }

    @Override
    public List<Group> findGroupsByNativeQuery(final Map<String, Object> parameterMap, final int firstResult, final int maxResults) {
        throw new ActivitiException("Process user group manager doesn't support native query on group");
    }

    @Override
    public long findGroupCountByNativeQuery(final Map<String, Object> parameterMap) {
        throw new ActivitiException("Process user group manager doesn't support native query on group count");
    }

    @Override
    public Group createNewGroup(final String groupId) {
        throw new ActivitiException("Process user group manager doesn't support creating a new group");
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        throw new ActivitiException("Process user group manager doesn't support creating a new group");
    }

    @Override
    public boolean isNewGroup(final Group group) {
        throw new ActivitiException("Process user group manager doesn't support checking is new group");
    }

    @Override
    public GroupEntity create() {
        throw new ActivitiException("Process user group manager doesn't support creating a new group");
    }



    @Override
    public void insert(final GroupEntity entity) {
        throw new ActivitiException("Process user group manager doesn't support creating a new group");
    }

    @Override
    public void insert(final GroupEntity entity, final boolean fireCreateEvent) {
        throw new ActivitiException("Process user group manager doesn't support creating a new group");
    }

    @Override
    public GroupEntity update(final GroupEntity entity) {
        throw new ActivitiException("Process user group manager doesn't support updating a group");
    }

    @Override
    public GroupEntity update(final GroupEntity entity, final boolean fireUpdateEvent) {
        throw new ActivitiException("Process user group manager doesn't support updating a group");
    }

    @Override
    public void delete(final String id) {
        throw new ActivitiException("Process user group manager doesn't support delete a group");
    }

    @Override
    public void delete(final GroupEntity entity) {
        throw new ActivitiException("Process user group manager doesn't support delete a group");
    }

    @Override
    public void delete(final GroupEntity entity, final boolean fireDeleteEvent) {
        throw new ActivitiException("Process user group manager doesn't support delete a group");
    }

    private GroupEntity mapUserGroupToGroupEntity(UserGroup userGroup) {
        GroupEntity groupEntity = new GroupEntityImpl();
        groupEntity.setName(userGroup.getName());
        groupEntity.setType(userGroup.getType());
        return groupEntity;
    }
}
