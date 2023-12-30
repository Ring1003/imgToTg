package com.cherry.img.Controller.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.util.PoitlIOUtils;
import org.ddr.poi.html.HtmlRenderPolicy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/word")
public class WordDemoController {



    @GetMapping("export")
    public void testWordExport(HttpServletResponse response) throws IOException {
        System.err.println("测试word导出");

        // html渲染插件
        HtmlRenderPolicy htmlRenderPolicy = new HtmlRenderPolicy();
        Configure configure = Configure.builder()
                .bind("author", htmlRenderPolicy)
                .bind("probContent", htmlRenderPolicy)
                .build();

        XWPFTemplate template = XWPFTemplate.compile("/Users/meng/Downloads/aaaaabbbbb/template.docx",configure)
                .render(new HashMap<String, Object>() {{
                    put("probContent","<p>测试富文本</p><h1>富文本一级标题</h1>");
                    put("author", "<h2>MengJie</h2>");
                    put("projectName","--测试word--测试word--测试word--测试word--测试word--测试word--测试word--测试word--测试word");
                }});


        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition","attachment;filename=\""+"out_template.docx"+"\"");

        OutputStream out = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        template.write(bos);
        bos.flush();
        out.flush();
        PoitlIOUtils.closeQuietlyMulti(template, bos, out);

    }


}
