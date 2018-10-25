package nu.ssis.a18mosu.service;

import java.util.Optional;

import nu.ssis.a18mosu.datatransferobject.RemoteGenericBookDTO;

public interface RemoteBookApiClient {
	
	public Optional<RemoteGenericBookDTO> getRemoteGenericBook(String isbn);

}
