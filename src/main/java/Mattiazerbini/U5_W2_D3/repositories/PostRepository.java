package Mattiazerbini.U5_W2_D3.repositories;

import Mattiazerbini.U5_W2_D3.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
