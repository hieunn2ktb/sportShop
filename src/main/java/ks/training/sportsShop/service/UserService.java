package ks.training.sportsShop.service;

import ks.training.sportsShop.entity.Role;
import ks.training.sportsShop.entity.User;
import ks.training.sportsShop.repository.OrderRepository;
import ks.training.sportsShop.repository.ProductRepository;
import ks.training.sportsShop.repository.RoleRepository;
import ks.training.sportsShop.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.roleRepository = roleRepository;
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public List<User>getAllUserByEmail(String email){
        return this.userRepository.findOneByEmail(email);
    }
    public Page<User> getAllUsers(Pageable page) {
        return this.userRepository.findAll(page);
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User handleSaveUser(User user) {
        User eric = this.userRepository.save(user);
        System.out.println(eric);
        return eric;
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }
    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

//    public User registerDTOtoUser(RegisterDTO registerDTO) {
//        User user = new User();
//        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
//        user.setEmail(registerDTO.getEmail());
//        user.setPassword(registerDTO.getPassword());
//        return user;
//    }
}
