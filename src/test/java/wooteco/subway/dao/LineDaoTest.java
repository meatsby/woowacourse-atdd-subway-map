package wooteco.subway.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.domain.Line;
import wooteco.subway.domain.Station;

public class LineDaoTest {

    @DisplayName("노선을 저장한다.")
    @Test
    void save() {
        Line line = new Line("2호선", "green");
        Line savedLine = LineDao.save(line);

        assertThat(line.getName()).isEqualTo(savedLine.getName());
    }

    @DisplayName("같은 이름의 노선을 저장하는 경우 예외가 발생한다.")
    @Test
    void saveExistingName() {
        Line line = new Line("2호선", "green");
        LineDao.save(line);

        assertThatThrownBy(() -> {
            LineDao.save(line);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 이름의 노선은 등록할 수 없습니다.");
    }

    @DisplayName("모든 지하철 노선을 조회한다.")
    @Test
    void findAll() {
        Line line1 = new Line("2호선", "green");
        Line line2 = new Line("3호선", "orange");
        Line line3 = new Line("8호선", "pink");
        LineDao.save(line1);
        LineDao.save(line2);
        LineDao.save(line3);

        assertThat(LineDao.findAll().size()).isEqualTo(3);
    }

    @DisplayName("지하철 노선을 조회한다.")
    @Test
    void findById() {
        Line line = new Line("2호선", "green");
        Line savedLine = LineDao.save(line);

        Line foundLine = LineDao.findById(savedLine.getId());

        assertThat(foundLine.getName()).isEqualTo(savedLine.getName());
    }

    @DisplayName("지하철 노선을 수정한다.")
    @Test
    void updateLine() {
        Line line = new Line("2호선", "green");
        Line savedLine = LineDao.save(line);

        Line updatedLine = LineDao.update(savedLine.getId(), "3호선", "orange");

        assertThat(updatedLine.getName()).isEqualTo("3호선");
    }

    @DisplayName("지하철 노선을 삭제한다.")
    @Test
    void deleteById() {
        Line line = new Line("2호선", "green");
        Line savedLine = LineDao.save(line);

        LineDao.deleteById(savedLine.getId());

        assertThat(LineDao.findAll().size()).isZero();
    }

    @DisplayName("존재하지 않는 노선을 삭제할 경우 예외가 발생한다.")
    @Test
    void deleteNotExistingStation() {
        assertThatThrownBy(() -> LineDao.deleteById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 노선입니다.");
    }

    @AfterEach
    void reset() {
        List<Line> lines = LineDao.findAll();
        lines.clear();
    }
}
