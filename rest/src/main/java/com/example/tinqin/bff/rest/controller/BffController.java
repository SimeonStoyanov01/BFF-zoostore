package com.example.tinqin.bff.rest.controller;

import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdOperation;
import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdRequest;
import com.example.tinqin.bff.api.operation.item.getby.id.BffItemGetByIdResponse;
import com.example.tinqin.bff.api.operation.item.getby.title.ItemGetByTitleOperation;
import com.example.tinqin.bff.api.operation.item.getby.title.ItemGetByTitleRequest;
import com.example.tinqin.bff.api.operation.item.getby.title.ItemGetByTitleResponse;
import com.example.tinqin.bff.api.operation.tag.getitem.TagGetItemOperation;
import com.example.tinqin.bff.api.operation.tag.getitem.TagGetItemRequest;
import com.example.tinqin.bff.api.operation.tag.getitem.TagGetItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bffitem")
public class BffController {

    private final BffItemGetByIdOperation getByIdOperation;
    private final TagGetItemOperation tagGetItemOperation;

    private final ItemGetByTitleOperation itemGetByTitleOperation;

    @Autowired
    public BffController(BffItemGetByIdOperation getByIdOperation, TagGetItemOperation tagGetItemOperation, ItemGetByTitleOperation itemGetByTitleOperation) {
        this.getByIdOperation = getByIdOperation;
        this.tagGetItemOperation = tagGetItemOperation;
        this.itemGetByTitleOperation = itemGetByTitleOperation;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BffItemGetByIdResponse> getItem(BffItemGetByIdRequest bffItemGetByIdRequest) {
        return ResponseEntity.ok(getByIdOperation.process(bffItemGetByIdRequest));
    }

    @GetMapping("/tagName")
    public ResponseEntity<TagGetItemResponse> tagGetItem
            (@RequestParam(name = "tagName") String tagName,
             @RequestParam(name = "page") Integer page,
             @RequestParam(name = "size") Integer size) {
        TagGetItemRequest tagGetItemRequest = TagGetItemRequest
                .builder()
                .title(tagName)
                .page(page)
                .size(size)
                .build();
        return ResponseEntity.ok(tagGetItemOperation.process(tagGetItemRequest));


    }

    @GetMapping("/title")
    public ResponseEntity<ItemGetByTitleResponse> getItemsByTitle
            (@RequestParam(name = "title") String title,
             @RequestParam(name = "page") Integer page,
             @RequestParam(name = "size") Integer size) {

        ItemGetByTitleRequest itemGetByTitleRequest = ItemGetByTitleRequest
                .builder()
                .title(title)
                .page(page)
                .size(size)
                .build();
        return ResponseEntity.ok(itemGetByTitleOperation.process(itemGetByTitleRequest));
    }
}
