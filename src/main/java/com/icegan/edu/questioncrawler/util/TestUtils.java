package com.icegan.edu.questioncrawler.util;

import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import java.net.MalformedURLException;
import java.net.URL;

public class TestUtils {

    public static void doc4jHtml2Word(){
        try{
            URL url = new URL("https://news.163.com/19/0531/10/EGGET3DA000189FH.html");
            //“http://news.cctv.com/2019/05/30/ARTIKUzYcEgVHC4KFr2pbNo9190530.shtml”
            //http://en.wikipedia.org/w/index.php?title=Microsoft_Word&printable=yes

            String urlStr = "http://czsx.cooco.net.cn/test/";
            Document doc = Jsoup.connect(urlStr).get();
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);
            // Create an empty docx package
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.valueOf("A4"), true);

            /*NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
            wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
            ndp.unmarshalDefaultNumbering();*/

            // Convert the XHTML, and add it into the empty docx we made
            XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
            XHTMLImporter.setHyperlinkStyle("Hyperlink");
            wordMLPackage.getMainDocumentPart().getContent().addAll(
                    XHTMLImporter.convert(doc.html().replaceAll("&","&amp;"),urlStr) );

            System.out.println(
                    XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(), true, true));

            String savePath = System.getProperty("user.dir") + "/OUT_ConvertInXHTMLURL.docx";
            System.out.println(savePath);
            wordMLPackage.save(new java.io.File(savePath) );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String html = "http://czwl.cooco.net.cn/data:image/png;base64sdfsdfsdfsdfsfsdfsds";
        if(html.contains("data:image")){
            int index = html.lastIndexOf("//")+2;
            int end = html .indexOf("cooco.net.cn")-1;
            System.out.println(html.substring(index,end));
        }
    }
}
