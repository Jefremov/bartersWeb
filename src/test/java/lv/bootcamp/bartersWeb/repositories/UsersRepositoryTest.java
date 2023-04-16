package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersRepositoryTest {
    @Mock
    private UsersRepository usersRepository;

    @Test
    public void testFindUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(usersRepository.findUserByUsername(username)).thenReturn(user);

        User foundUser = usersRepository.findUserByUsername(username);

        assertEquals(user, foundUser);
    }

    @Test
    public void testExistsByUsername() {
        String username = "test";

        when(usersRepository.existsByUsername(username)).thenReturn(true);

        boolean result = usersRepository.existsByUsername(username);

        assertTrue(result);
    }

    @Test
    public void testExistsByEmail() {
        String email = "test@example.com";

        when(usersRepository.existsByEmail(email)).thenReturn(true);

        boolean result = usersRepository.existsByEmail(email);

        assertTrue(result);
    }

    @Test
    public void testFindByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> foundUser = usersRepository.findByUsername(username);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }
}
