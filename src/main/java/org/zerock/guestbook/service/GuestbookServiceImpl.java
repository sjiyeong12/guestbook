package org.zerock.guestbook.service;

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
}