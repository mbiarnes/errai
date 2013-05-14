package org.jboss.errai.security.demo.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.security.client.local.Identity;
import org.jboss.errai.security.demo.client.shared.MessageService;
import org.jboss.errai.security.shared.User;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
@Templated("#main")
@Page
public class ItemListPage extends Composite {
  @Inject
  private Identity identity;

  @Inject
  private Caller<MessageService> messageServiceCaller;

  @Inject
  @DataField("newItemForm")
  private Label label;

  @Inject
  @DataField
  private Button hello;

  @EventHandler
  private void onHelloClicked(ClickEvent event) {
    identity.getUser(new AsyncCallback<User>() {
      @Override
      public void onSuccess(User result) {
        //if the user is null that means we are not logged in, but calling this method anyway will
        //invoke the SecurityInterceptor that will redirect us to the login page.
        final String name = result != null ? result.getFullName() : WelcomePage.ANONYMOUS;

        messageServiceCaller.call(new RemoteCallback<String>() {
                                    @Override
                                    public void callback(String o) {
                                      label.setText(o);
                                    }
                                  }, new ErrorCallback<Object>() {
                                    @Override
                                    public boolean error(Object o, Throwable throwable) {
                                      throwable.printStackTrace();
                                      return false;
                                    }
                                  }
        ).hello(name);
      }

      @Override
      public void onFailure(Throwable caught) {
      }
    });
  }
}