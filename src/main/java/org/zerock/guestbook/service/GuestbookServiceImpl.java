package org.zerock.guestbook.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {
    private final GuestbookRepository gbRepository;

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = gbRepository.findById(gno);
        return result.isPresent() ? entityToDTO(result.get()) : null;
    }

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("register dto:" + dto);
        Guestbook entity = dtoToEntity(dto);
        gbRepository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result = gbRepository.findAll(pageable);
        Function<Guestbook, GuestbookDTO> fn = new Function<Guestbook, GuestbookDTO>() {
            @Override
            public GuestbookDTO apply(Guestbook entity) {
                return entityToDTO(entity);
            }
        };
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public void remove(Long gno) {
        log.info("remove..." + gno);
        gbRepository.deleteById(gno);

    }

    @Override
    public void modify(GuestbookDTO dto) {
        log.info("modify..." + dto);
        // Entity를 먼저 찾는 이유: Entity가 있어야 부분 변경이 가능
        Optional<Guestbook> result = gbRepository.findById(dto.getGno());
        if (result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle()); // 부분만 바꿈
            entity.changeCOntent(dto.getContent()); // 부분만 바꿈
            gbRepository.save(entity);
        }

    }
}