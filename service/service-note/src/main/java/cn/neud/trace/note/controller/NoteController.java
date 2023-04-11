package cn.neud.trace.note.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.neud.trace.note.model.dto.Result;
import cn.neud.trace.note.model.dto.UserDTO;
import cn.neud.trace.note.model.entity.Note;
import cn.neud.trace.note.service.NoteService;
import cn.neud.trace.note.constant.SystemConstants;
import cn.neud.trace.note.utils.UserLocal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author David
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    @Resource
    private NoteService noteService;

    @PostMapping
    public Result saveNote(@RequestBody Note note) {
        return noteService.saveNote(note);
    }

    @PutMapping("/like/{id}")
    public Result likeNote(@PathVariable("id") Long id) {
        return noteService.likeNote(id);
    }

    @GetMapping("/of/me")
    public Result queryMyNote(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserLocal.getUser();
        // 根据用户查询
        Page<Note> page = noteService.query()
                .eq("user_id", user.getId()).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Note> records = page.getRecords();
        return Result.ok(records);
    }

    @GetMapping("/hot")
    public Result queryHotNote(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        return noteService.queryHotNote(current);
    }

    @GetMapping("/{id}")
    public Result queryNoteById(@PathVariable("id") Long id) {
        return noteService.queryNoteById(id);
    }

    @GetMapping("/likes/{id}")
    public Result queryNoteLikes(@PathVariable("id") Long id) {
        return noteService.queryNoteLikes(id);
    }

    @GetMapping("/of/user")
    public Result queryNoteByUserId(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam("id") Long id) {
        // 根据用户查询
        Page<Note> page = noteService.query()
                .eq("user_id", id).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Note> records = page.getRecords();
        return Result.ok(records);
    }

    @GetMapping("/of/follow")
    public Result queryNoteOfFollow(
            @RequestParam("lastId") Long max, @RequestParam(value = "offset", defaultValue = "0") Integer offset){
        return noteService.queryNoteOfFollow(max, offset);
    }
}
