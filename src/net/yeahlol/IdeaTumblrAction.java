package net.yeahlol;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Post;

import net.yeahlol.util.TokenizeUtils;

public class IdeaTumblrAction extends AnAction {
    private TokenizeUtils tokenizeUtils;
    private String title = "Welcome";

    public IdeaTumblrAction() {
        //super(null, null, IconLoader.findIcon("/net/yeahlol/icons/tumblr_mini.png"));
        tokenizeUtils = new TokenizeUtils();
    }

    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getData(PlatformDataKeys.PROJECT);
        String originalText = Messages.showInputDialog(project, "What content do you see?",
                title, IconLoader.findIcon("/net/yeahlol/icons/tumblr.png"));
        if (originalText != null && originalText.length() == 0) {
            Messages.showErrorDialog(project, "Please enter one or more letters.", "Can't analyze.");
        } else if (originalText != null && originalText.length() > 30) {
            Messages.showErrorDialog(project, "Over 30 letter.", "Can't analyze.");
        }
        List<String> tokens = tokenizeUtils.getTokens(originalText);
        Random random = new Random();
        int urlRanNum = random.nextInt(21);
        String url = getTaggedContent(tokens).get(urlRanNum);
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException ex) {
            Messages.showErrorDialog(project, "Fail..", "URISyntax error");
        } catch (IOException ex) {
            Messages.showErrorDialog(project, "Fail..", "IO error");
        }
    }

    public List<String> getTaggedContent(List<String> tokens) {
        JumblrClient client = authorize();
        List<String> postUrls = new ArrayList<String>();
        Random random = new Random();
        int tokenRanNum = random.nextInt(tokens.size());
        for (Post post: client.tagged(tokens.get(tokenRanNum))) {
            postUrls.add(post.getPostUrl());
        }
        return postUrls;
    }

    public JumblrClient authorize() {
        final String consumerKey = "CONSUMER_KEY";
        final String consumerSecret = "CONSUMER_SECRET";
        final String oauthToken = "OAUTH_TOKEN";
        final String oauthTokenSecret = "OAUTH_TOKEN_SECRET";

        JumblrClient client = new JumblrClient(consumerKey, consumerSecret);
        client.setToken(oauthToken, oauthTokenSecret);
        return client;
    }
}