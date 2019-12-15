
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import sample.ConnectionHandler;
import sample.MessagesRetriever;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessagesRetrieverTest {

    private MessagesRetriever sut;

    @Mock
    private ConnectionHandler connectionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new MessagesRetriever(connectionHandler);
    }

    @Test
    public void shouldSeparateMessageCorrectly() throws IOException {
        when(connectionHandler.receiveMessage()).thenReturn("aa//bb//cc//");
        sut.readMessage();
        List<String> expected = Arrays.asList("aa", "bb", "cc");

        assertEquals(expected, sut.getMessages());
        assertEquals("", sut.getIncompleteMessage());
    }
    @Test
    public void shouldAddToIncomplete() throws IOException {
        when(connectionHandler.receiveMessage()).thenReturn("aa//bb//cc");
        sut.readMessage();
        List<String> expected = Arrays.asList("aa", "bb");

        assertEquals(expected, sut.getMessages());
        assertEquals("cc", sut.getIncompleteMessage());
    }

    @Test
    public void shouldContinueIncomplete() throws IOException {
        when(connectionHandler.receiveMessage()).thenReturn("aa//bb//cc//");
        sut.setIncompleteMessage("dd");
        sut.readMessage();
        List<String> expected = Arrays.asList("ddaa", "bb", "cc");

        assertEquals(expected, sut.getMessages());
        assertEquals("", sut.getIncompleteMessage());
    }

}