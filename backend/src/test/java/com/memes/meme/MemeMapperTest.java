package com.memes.meme;

import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.test.MemeTestFactory;
import com.memes.template.test.TemplateTestFactory;
import com.memes.user.UserMapperImpl;
import com.memes.user.test.UserTestFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class MemeMapperTest {

  private final String PREFIX = "prefix";
  private MemeMapper memeMapper;

  @BeforeEach
  void setUp() {
    this.memeMapper = new MemeMapperImpl(new UserMapperImpl());
    memeMapper.setImageUrlPrefix(PREFIX);
  }

  @Nested
  class MemeToMemeResponseDto {

    @Test
    @DisplayName("Should map Meme to MemeResponseDto")
    void mapsToDto() {

      Meme providedMeme =
          MemeTestFactory.meme()
              .author(UserTestFactory.user().build())
              .template(TemplateTestFactory.template().build())
              .build();

      MemeResponseDto actualResult = memeMapper.memeToMemeResponseDto(providedMeme);

      SoftAssertions.assertSoftly(
          softly -> {
            softly.assertThat(actualResult.getCreated()).isEqualTo(providedMeme.getCreated());
            softly
                .assertThat(actualResult.getAuthor().getEmail())
                .isEqualTo(providedMeme.getAuthor().getEmail());
            softly
                .assertThat(actualResult.getTemplate().getId())
                .isEqualTo(providedMeme.getTemplate().getId());
            softly.assertThat(actualResult.getId()).isEqualTo(providedMeme.getId());
          });
    }

    @Test
    @DisplayName("Should prefix filename with string provided as a property source")
    void addsPrefix() {
      Meme providedMeme = MemeTestFactory.meme().build();

      MemeResponseDto actualResult = memeMapper.memeToMemeResponseDto(providedMeme);

      assertThat(actualResult.getUrl()).isEqualTo(PREFIX + providedMeme.getFilename());
    }
  }
}
