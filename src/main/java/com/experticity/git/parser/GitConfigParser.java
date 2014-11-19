package com.experticity.git.parser;

import static java.lang.String.format;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GitConfigParser
{
    private static final String CONTENT = "[core]\n"
            + "\trepositoryformatversion = 0\n"
            + "\tfilemode = true\n"
            + "\tbare = false\n"
            + "\tlogallrefupdates = true\n"
            + "[svn-remote \"svn\"]\n"
            + "\turl = https://dev-svn/development\n"
            + "\tfetch = 3point5/4.x/memberWeb/trunk:refs/remotes/svn/trunk";

    private static final String FORMAT = "3point5/4.x/memberWeb/branches/%s:refs/remotes/svn/%s";

    public static void main(String[] args) throws Exception
    {
        String xml = "<ul>\n"
                + "  <li><a href=\"../\">..</a></li>\n"
                + "  <li><a href=\"TPF-18772/\">TPF-18772/</a></li>\n"
                + "  <li><a href=\"TPF-19826/\">TPF-19826/</a></li>\n"
                + "  <li><a href=\"TPF-20227/\">TPF-20227/</a></li>\n"
                + "  <li><a href=\"TPF-20269-user-details/\">TPF-20269-user-details/</a></li>\n"
                + "  <li><a href=\"TPF-20335/\">TPF-20335/</a></li>\n"
                + "  <li><a href=\"TPF-20502/\">TPF-20502/</a></li>\n"
                + "  <li><a href=\"TPF_20057/\">TPF_20057/</a></li>\n"
                + "  <li><a href=\"aggregators/\">aggregators/</a></li>\n"
                + "  <li><a href=\"aggregators-pentaho5/\">aggregators-pentaho5/</a></li>\n"
                + "  <li><a href=\"bazinga-ftp/\">bazinga-ftp/</a></li>\n"
                + "  <li><a href=\"bazinga-integrationlite/\">bazinga-integrationlite/</a></li>\n"
                + "  <li><a href=\"bazinga-jobs/\">bazinga-jobs/</a></li>\n"
                + "  <li><a href=\"bazinga-pci/\">bazinga-pci/</a></li>\n"
                + "  <li><a href=\"bazinga-trainingSummary/\">bazinga-trainingSummary/</a></li>\n"
                + "  <li><a href=\"bazinga-ux/\">bazinga-ux/</a></li>\n"
                + "  <li><a href=\"checkin-tests/\">checkin-tests/</a></li>\n"
                + "  <li><a href=\"dragondrop/\">dragondrop/</a></li>\n"
                + "  <li><a href=\"dragondrop-UIrollbar/\">dragondrop-UIrollbar/</a></li>\n"
                + "  <li><a href=\"dragondrop-catfilter/\">dragondrop-catfilter/</a></li>\n"
                + "  <li><a href=\"dragondrop-https/\">dragondrop-https/</a></li>\n"
                + "  <li><a href=\"dragondrop-intlc/\">dragondrop-intlc/</a></li>\n"
                + "  <li><a href=\"dragondrop-meow/\">dragondrop-meow/</a></li>\n"
                + "  <li><a href=\"dragondrop-mmf/\">dragondrop-mmf/</a></li>\n"
                + "  <li><a href=\"dragondrop-mmf2/\">dragondrop-mmf2/</a></li>\n"
                + "  <li><a href=\"dragondrop-promotiveHomepage/\">dragondrop-promotiveHomepage/</a></li>\n"
                + "  <li><a href=\"dragondrop-promotiveportals/\">dragondrop-promotiveportals/</a></li>\n"
                + "  <li><a href=\"dragondrop-retailaff/\">dragondrop-retailaff/</a></li>\n"
                + "  <li><a href=\"dragondrop-roles/\">dragondrop-roles/</a></li>\n"
                + "  <li><a href=\"dragondrop-signon/\">dragondrop-signon/</a></li>\n"
                + "  <li><a href=\"dragondrop-trans/\">dragondrop-trans/</a></li>\n"
                + "  <li><a href=\"dragondrop-trans-email/\">dragondrop-trans-email/</a></li>\n"
                + "  <li><a href=\"dragondrop-trans2/\">dragondrop-trans2/</a></li>\n"
                + "  <li><a href=\"dragondrop-usercats/\">dragondrop-usercats/</a></li>\n"
                + "  <li><a href=\"elmegatrono/\">elmegatrono/</a></li>\n"
                + "  <li><a href=\"graybeard/\">graybeard/</a></li>\n"
                + "  <li><a href=\"grunt-less/\">grunt-less/</a></li>\n"
                + "  <li><a href=\"import-TPF-18192/\">import-TPF-18192/</a></li>\n"
                + "  <li><a href=\"location-TPF-18213/\">location-TPF-18213/</a></li>\n"
                + "  <li><a href=\"megatron/\">megatron/</a></li>\n"
                + "  <li><a href=\"megatron_old/\">megatron_old/</a></li>\n"
                + "  <li><a href=\"patch-prod-TPF-18668/\">patch-prod-TPF-18668/</a></li>\n"
                + "  <li><a href=\"patch_TPF-17905/\">patch_TPF-17905/</a></li>\n"
                + "  <li><a href=\"pom-wonderful/\">pom-wonderful/</a></li>\n"
                + "  <li><a href=\"qa-testdataset/\">qa-testdataset/</a></li>\n"
                + "  <li><a href=\"sharktopus/\">sharktopus/</a></li>\n"
                + "  <li><a href=\"sharktopusEeh/\">sharktopusEeh/</a></li>\n"
                + "  <li><a href=\"sharktopusTargetingPhase2/\">sharktopusTargetingPhase2/</a></li>\n"
                + "  <li><a href=\"sharktopusTargetingPhase2Old/\">sharktopusTargetingPhase2Old/</a></li>\n"
                + "  <li><a href=\"sonarqube_fixes/\">sonarqube_fixes/</a></li>\n"
                + "  <li><a href=\"techServices/\">techServices/</a></li>\n"
                + "  <li><a href=\"techServices2/\">techServices2/</a></li>\n"
                + "  <li><a href=\"techServices3/\">techServices3/</a></li>\n"
                + "  <li><a href=\"testSourceRestructure/\">testSourceRestructure/</a></li>\n"
                + "  <li><a href=\"testSourceRestructure2/\">testSourceRestructure2/</a></li>\n"
                + "  <li><a href=\"ux-updates/\">ux-updates/</a></li>\n"
                + " </ul>";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        Document document = builder.parse(is);

        NodeList lists = document.getElementsByTagName("li");

        StringBuilder sb = new StringBuilder(CONTENT);


        for(int i = 0; i < lists.getLength(); i++)
        {

            Node item = lists.item(i);
            String text = item.getTextContent();
            if(!"..:".contains(text))
            {
                sb.append("\n");
                sb.append("\t");
                sb.append(format(FORMAT, text, text));
            }

        }

        System.out.println(sb);
    }
}
