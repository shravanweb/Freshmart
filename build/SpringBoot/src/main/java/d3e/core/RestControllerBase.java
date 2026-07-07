package d3e.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import gqltosql.schema.IModelSchema;
import rest.JSONInputContext;
import store.DBObject;
import store.DatabaseObject;

public abstract class RestControllerBase {
    @Autowired private HttpContext ctx;
    
    @Autowired
    private D3ETempResourceHandler saveHandler;
    
    @Autowired
    private IModelSchema schema;
    
    public HttpContext getContext() {
        return this.ctx;
    }
    
    protected void markNotFound() {
		ctx.getResponse().setStatus(HttpStatus.NOT_FOUND.value());
	}

	protected void markForbidden() {
		ctx.getResponse().setStatus(HttpStatus.FORBIDDEN.value());
	}
	
	protected void markServerError() {
		ctx.getResponse().setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	protected void markBadRequest() {
		ctx.getResponse().setStatus(HttpStatus.BAD_REQUEST.value());
	}
    
    protected DFile uploadFile(MultipartFile file) {
    	String fileName = file.getOriginalFilename();
        try {
            DFile result = saveHandler.save(fileName, file.getInputStream());
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
        }
    }
    
    protected List<DFile> uploadFiles(List<MultipartFile> files) {
    	return files.stream().map(f -> uploadFile(f)).collect(Collectors.toList());
    }
    
    protected <T extends DBObject> T convertFromJson(String json, String type) {
    	try {
    		return JSONInputContext.parse(json, type, schema);
    	} catch (RuntimeException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    protected <T extends DBObject> List<T> convertCollFromJson(String json, String type) {
    	try {
    		return JSONInputContext.parseColl(json, type, schema);
    	} catch (RuntimeException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    protected void writeObjectAsJson(String type, Object obj) {
    	String json = JSONInputContext.toJsonString((DatabaseObject) obj, type, schema);
    	writeJsonToResponse(json);
    }
    
    protected <T extends DBObject> T updateObjectFromJson(T obj, String json, String type) {
    	return JSONInputContext.readToObjFromJson(obj, json, type, schema);
    }
    
    protected void writeJsonToResponse(String json) {
    	if (json == null || json.isEmpty()) {
    		return;
    	}
    	HttpServletResponse response = ctx.getResponse();
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			markServerError();
		}
    }
}
