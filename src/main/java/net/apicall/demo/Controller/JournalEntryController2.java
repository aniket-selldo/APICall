package net.apicall.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.apicall.demo.Entity.journalEntry;

@RestController
@RequestMapping("/journal")
public class JournalEntryController2 {

	private Map <Double,journalEntry> JournalEntry = new HashMap<>();
	
	@GetMapping
	public List<journalEntry> getAll(){
		return new ArrayList<>(JournalEntry.values());
	}
	
	@PostMapping
	public journalEntry createEntry(@RequestBody journalEntry je) {
		JournalEntry.put(je.getId(), je);
		return JournalEntry.get(je.getId());
	}
	
	@PostMapping("/add")
	public String addition(@RequestBody journalEntry je) {
		JournalEntry.put(je.getId(), je);
		return "Values for addition is >> "+(je.getValue_1()+je.getValue_2());
	}
	
	@PostMapping("/sub")
	public String substraction(@RequestBody journalEntry je) {
		JournalEntry.put(je.getId(), je);
		return "Values for substraction is >> "+(je.getValue_1()-je.getValue_2());
	}
	
	@GetMapping("id/{myId}")
	public journalEntry getById(@PathVariable double myId){
		return JournalEntry.get(myId);
	}
	
	@DeleteMapping("id/{myId}")
	public journalEntry deleteById(@PathVariable double myId){
		return JournalEntry.remove(myId);
	}
	
	@PutMapping("id/{myId}")
	public journalEntry updateById(@PathVariable double myId, @RequestBody journalEntry je ){
		return JournalEntry.put(myId,je);
	}
	
}











