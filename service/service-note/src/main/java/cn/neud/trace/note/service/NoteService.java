package cn.neud.trace.note.service;

import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author David
 * @since 2021-12-22
 */
public interface NoteService extends IService<Note> {

    Result queryHotNote(Integer current);

    Result queryNoteById(Long id);

    Result likeNote(Long id);

    Result queryNoteLikes(Long id);

    Result saveNote(Note note);

    Result queryNoteOfFollow(Long max, Integer offset);

}
