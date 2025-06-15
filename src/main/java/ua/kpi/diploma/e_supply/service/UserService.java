package ua.kpi.diploma.e_supply.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.kpi.diploma.e_supply.dto.UserInfoDTO;
import ua.kpi.diploma.e_supply.entity.Users;
import ua.kpi.diploma.e_supply.repository.UsersRepository;
import ua.kpi.diploma.e_supply.service.UserService;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public UserInfoDTO getUserInfoByEmail(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email: " + email));

        return new UserInfoDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getPatronomicName(),
                user.getCreationDate(),
                user.getRank() != null ? user.getRank().getTitle() : null,
                user.getPosition() != null ? user.getPosition().getTitle() : null,
                user.getUnit() != null ? user.getUnit().getName() : null,
                user.getRole() != null ? user.getRole().getTitle() : null,
                user.getEmail()
        );
    }
}
