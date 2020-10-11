package com.memes.meme;

import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.test.MemeTestBuilder;
import com.memes.template.test.TemplateTestBuilder;
import com.memes.user.UserMapperImpl;
import com.memes.user.test.UserTestBuilder;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MemeMapperImpl.class, UserMapperImpl.class})
@TestPropertySource(properties = "images.url-prefix=prefix")
class MemeMapperTest {

  private final String PREFIX = "prefix";
  @Autowired MemeMapper memeMapper;

  @Nested
  class MemeToMemeResponseDto {

    @Test
    @DisplayName("Should map Meme to MemeResponseDto")
    void mapsToDto() {
      Meme providedMeme =
          MemeTestBuilder.meme()
              .author(UserTestBuilder.user().build())
              .template(TemplateTestBuilder.template().build())
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
      Meme providedMeme = MemeTestBuilder.meme().build();

      MemeResponseDto actualResult = memeMapper.memeToMemeResponseDto(providedMeme);

      assertThat(actualResult.getUrl()).isEqualTo(PREFIX + providedMeme.getFilename());
    }
  }
}
