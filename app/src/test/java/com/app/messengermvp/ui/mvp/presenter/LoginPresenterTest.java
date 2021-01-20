package com.app.messengermvp.ui.mvp.presenter;

import androidx.fragment.app.Fragment;
import com.app.messengermvp.ui.fragments.LoginFragment;
import com.app.messengermvp.ui.mvp.model.LoginModel;
import com.app.messengermvp.ui.mvp.view.LoginView;

import junit.framework.TestCase;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class LoginPresenterTest {

//    @Test
//    public void onLoginClickEvent() {
//    }
//
//    @Test
//    public void onLoginCompleteEvent() {
//    }

    //private static final Random RANDOM = new Random();

    @Mock // 1
    private LoginView view;

    @Mock // 1
    private LoginModel model;

    private LoginPresenter presenter;

    @Captor // 3
    private ArgumentCaptor<LoginModel.LoginCompleteEvent> argumentCaptor;

    @Before // 2
    public void setUp(){
        // A convenient way to inject mocks by using the @Mock annotation in Mockito.
        //  For mock injections , initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // get the presenter reference and bind with view for testing

        //fragment = new LoginFragment();

        presenter = new LoginPresenter(
                view,
                model
        );
        presenter.initView();

    }

    @Test
    public void loginCompleteEvent() throws Exception {

        EventBus eventBus = Mockito.mock(EventBus.class);
        eventBus.post(new LoginModel.LoginCompleteEvent(true, "shit@gmail.com"));

        Mockito.verify(eventBus, Mockito.times(1)).post(argumentCaptor.capture());

        LoginModel.LoginCompleteEvent event = argumentCaptor.getValue();

        assertTrue(event.isTaskSuccessful());


    }
}