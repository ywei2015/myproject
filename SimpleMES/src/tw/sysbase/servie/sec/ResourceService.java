/**
 * 
 */
package tw.sysbase.servie.sec;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sysbase.dao.GenericDao;
import tw.sysbase.entity.sec.Resources;
import tw.sysbase.entity.sec.ResourceImpl;

/**
 * @author 刘威
 * 
 */
@Service
@Transactional
public class ResourceService {

	// ****************************************************
	//
	// 基本API
	//
	// ****************************************************
	@Resource
	private GenericDao genericDao;
	/**
	 * 创建资源
	 * 
	 * @param targetId
	 *            String:目标ID
	 * @param type
	 *            String:目标类型
	 * @return 资源持久对象
	 */	
	public Resources createResource(String targetId, String type) {
		if (StringUtils.isBlank(targetId)) {
			throw new RuntimeException(
					"ResourceManager.createResource方法中targetId为空!");
		}
		if (StringUtils.isBlank(type)) {
			throw new RuntimeException(
					"ResourceManager.createResource方法中type为空!");
		}
		Resources resource = new ResourceImpl(targetId, type);
		saveResource(resource);
		return resource;
	}

	/**
	 * 创建资源
	 * 
	 * @param resource
	 *            Resource:创建
	 * @return 资源持久对象
	 */
	public Resources saveResource(Resources resource) {
		if (resource == null) {
			throw new RuntimeException(
					"ResourceManager.saveResource方法中resource对象为空!");
		}
		String targetId = resource.getTargetId();
		String type = resource.getType();
		if (StringUtils.isBlank(targetId)) {
			throw new RuntimeException(
					"ResourceManager.saveResource方法中targetId为空!");
		}
		if (StringUtils.isBlank(type)) {
			throw new RuntimeException("ResourceManager.saveResource方法中type为空!");
		}
		Resources existResource = findResourceByTargetIdAndType(targetId, type);
		if (existResource != null) {
			return existResource;
		}
		genericDao.save(resource);
		return resource;
	}
	
	/**
	 * 得到所有资源
	 * 
	 * @return 资源持久对象
	 */	
	public List<Resource> getAllResource() {
		return genericDao.getAll(ResourceImpl.class);
	}

	/**
	 * 通过资源Id查找资源
	 * 
	 * @param resourceId
	 *            String:资源Id
	 * @return 资源持久对象
	 */
	public Resource findResourceById(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new RuntimeException(
					"ResourceManager.findResourceById方法中resourceId为空!");
		}
		Resource resource = (Resource) genericDao.getById(
				ResourceImpl.class, resourceId);
		return resource;
	}
	
	/**
	 * 通过目标Id查找资源
	 * 
	 * @param targetId
	 *            String:目标Id
	 * @return 资源持久对象
	 */
	public List<Resource> findResourceByTargetId(String targetId) {
		if (StringUtils.isBlank(targetId)) {
			throw new RuntimeException(
					"ResourceManager.findResourceByTargetId方法中targetId为空!");
		}
		List<Resource> resources = genericDao.getList(
				"SECURITY.RESOURCE.FIND_BY_TARGERID", targetId);
		return resources;
	}
	
	/**
	 * 通过类型查找资源
	 * 
	 * @param targetId
	 *            String:目标Id
	 * @return 资源持久对象
	 */
	public List<Resource> findResourceByType(String type) {
		if (StringUtils.isBlank(type)) {
			throw new RuntimeException(
					"ResourceManager.findResourceByType方法中type为空!");
		}
		List<Resource> resources = genericDao.getList(
				"SECURITY.RESOURCE.FIND_BY_TYPE", type);
		return resources;
	}

	/**
	 * 通过目标ID和类型ID查找资源
	 * 
	 * @param targetId
	 *            String:目标Id
	 * @param type
	 *            String:类型Id
	 * @return 资源持久对象
	 */
	public Resources findResourceByTargetIdAndType(String targetId, String type) {
		if (StringUtils.isBlank(targetId)) {
			throw new RuntimeException(
					"ResourceManager.findResourceByType方法中targetId为空!");
		}
		if (StringUtils.isBlank(type)) {
			throw new RuntimeException(
					"ResourceManager.findResourceByType方法中type为空!");
		}
		String[] parameter = { targetId, type };
		List<Resources> resources = genericDao.getList(
				"SECURITY.RESOURCE.FIND_BY_TARGERID_AND_TYPE", parameter);
		if (resources.isEmpty()){
			return null;
		}		
		if (resources.size() >= 2) {
			throw new RuntimeException("同一类型targetId不允许有重复许可!");
		}
		return resources.iterator().next();
	}

	/**
	 * 删除资源通过资源实例
	 * 
	 * @param targetId
	 *            String:目标Id
	 * @param type
	 *            String:类型Id
	 * @return 资源持久对象
	 */
	public void deleteResource(Resources resource) {
		genericDao.delete(resource);
	}

	/**
	 * 删除资源通过资源实例ID
	 * 
	 * @param resourceId
	 *            String:资源Id
	 */	
	public void deleteResourceById(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new RuntimeException(
					"ResourceManager.deleteResourceById方法中resourceId为空!");
		}
		genericDao.deleteById(ResourceImpl.class, resourceId);
	}

	/**
	 * 删除资源通过目标ID
	 * 
	 * @param targetId
	 *            String:目标Id
	 */	
	public void deleteResourceByTargetId(String targetId) {
		if (StringUtils.isBlank(targetId)) {
			throw new RuntimeException(
					"ResourceManager.deleteResourceByTargetId方法中targetId为空!");
		}
		genericDao.execute("SECURITY.RESOURCE.DEL_BY_TARGERID", targetId);
	}

	/**
	 * 删除资源通过类型ID
	 * 
	 * @param type
	 *            String:类型Id
	 */	
	public void deleteResourceByType(String type) {
		if (StringUtils.isBlank(type)) {
			throw new RuntimeException(
					"ResourceManager.deleteResourceByType方法中type为空!");
		}
		genericDao.execute("SECURITY.RESOURCE.DEL_BY_TYPE", type);
	}

}
