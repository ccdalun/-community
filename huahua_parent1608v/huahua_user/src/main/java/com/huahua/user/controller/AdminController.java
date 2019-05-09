package com.huahua.user.controller;
import java.util.HashMap;
import java.util.Map;

import huahua.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huahua.user.pojo.Admin;
import com.huahua.user.service.AdminService;

import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	HttpServletRequest request;

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin){
		adminService.add(admin);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		//删除用户，必须拥有管理员权限，否则不能删除
		//前后端约定：前端请求微服务时需要添加头信息Authorization ,内容为Bearer+空格+token
		String header = request.getHeader("Authorization");//Authorization参数名称 获取请求头中的数据
		if(StringUtils.isEmpty(header)){
			return new Result(true,StatusCode.LOGINERROR,"登录有误，请重新登录");
		}
		//如果 Bearer也为空
		if(header.startsWith("Bearer ")){ //startsWith("Bearer ")：查询字符串中 以Bearer 为开头的数据是否存在
			return new Result(true,StatusCode.LOGINERROR,"登录有误，请重新登录");
		}
		//字符串第七位后截取字符串
		String token = header.substring(7);
		Claims claims = jwtUtil.parseJWT(token);
		//校验claims不能为空
		if(null == claims){
			return new Result(true,StatusCode.LOGINERROR,"登录异常");
		}
		//判断 必须拥有管理员权限，否则不能删除。
		if(StringUtils.equals("admin", (String) claims.get("roles"))){
			return new Result(true,StatusCode.AUTOROLES,"权限不足");
		}
		adminService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody Map<String,String> loginMap){
		Admin admin = adminService.findByloginnameAndPassword(loginMap.get("loginname"),loginMap.get("password"));
		if(null != admin){
			//生成token（JTW）处理用户一系列相关的信息，比如权限的查询，用户一些相关的业务
			String token = jwtUtil.createJWT(admin.getId(),admin.getLoginname(),"admin");
			//JWT前段与后端访问的唯一标示、都要校验token否则会让操作的用户返回登录
			//将toekn数据换回给前段（身份的标识
			Map map = new HashMap();
			map.put("token",token);
			map.put("name",admin.getLoginname());
			return  new Result(true,StatusCode.OK,"登陆成功",map);
		}else{
			return  new Result(false,StatusCode.LOGINERROR,"登陆失败,请重新输入用户名和密码");
		}
	}
}
